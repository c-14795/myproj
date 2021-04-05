package csvp;


import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.RowProcessor;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GSProcessor implements RowProcessor {

    private static List<String[]> linList = new ArrayList<String[]>();
     static long sequence = 0L;
    private static String[] BCP = null;
    private static String[] PER = null;
     static long totalCount =0l;
    private static List<String[]> ctbList = new ArrayList<String[]>();
    private static DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    static int currentCounter=0;
    static String counterC ="000000001";
    @Override
    public void processStarted(ParsingContext parsingContext) {
        try
        {
            File f = new File("counter");
            if(f.exists())
            {
                FileInputStream fi = new FileInputStream(new File("counter"));
                ObjectInputStream oi = new ObjectInputStream(fi);
                Counter c = (Counter) oi.readObject();
                currentCounter=c.getCount()+1;
                counterC=String.format("%09d", currentCounter);
                oi.close();
                fi.close();
                f.delete();

            }
            else
            {
                currentCounter=1;
                counterC=String.format("%09d", currentCounter);
            }
            FileOutputStream fo = new FileOutputStream(new File("counter"));
            ObjectOutputStream o = new ObjectOutputStream(fo);
            o.writeObject(new Counter(currentCounter));
            o.close();
            fo.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void rowProcessed(String[] strings, ParsingContext parsingContext) {


        if (strings[0].equals("HDR")) {
            ISA(strings);
        }

        if (strings[0].equals("00")) {
            BCP = strings;
        }

        if (BCP != null && strings[0].equals("05")) {
            BCT_CP(strings);
        }
        if (strings[0].equals("05")) {
            CTB(strings, false);
        }
        if (strings[0].equals("03") && ctbList.size() > 0) {
            CTB(strings, true);
        }

        if (strings[0].equals("06")) {
            sixN(strings);
        }

        if (strings[0].equals("07")) {
            sevenN(strings);
        }


        if (strings[0].equals("01") || strings[0].equals("02")) {
           // System.out.println(Arrays.toString(strings));
            generateLin(strings);
            if (strings[0].equals("02")) {
                linList.clear();
            }
        }

    }

    @Override
    public void processEnded(ParsingContext parsingContext) {

    }

    /**
     * LIN~LineNoSequence~MF~KCloumn~MG~Bcol~VP~Bcol~PD~Mcol~PI~Qcol~ZZ~ColT~CH~ColU
     * REF~ME~~Vcol
     * PID~F~~~~Ccol(2)
     * MEA~DE~TT~0~MO(CompleteLineHardCode)
     * LDT~AF~Ccol(1)~DW
     * CTP~~D01~Dcol(1)~1~EA
     */
    private static void generateLin(String[] currentRow) {
        linList.add(currentRow);

        if (linList.size() == 2) {
            String Mcol = linList.get(0)[12];
            if (Mcol.length() > 30) {
                Mcol = Mcol.substring(0, 31);
            }
            appendToFile("LIN~" + (++sequence) + "~MF~" + linList.get(0)[10] + "~MG~" +
                    linList.get(0)[1] + "~VP~" + linList.get(1)[1] + "~PD~" +
                    Mcol + "~PI~" + linList.get(0)[16] + "~ZZ~" +
                    linList.get(0)[19] + "~CH~" + linList.get(0)[20] + "\n" +
                    "REF~ME~~" + linList.get(0)[21] + "\n" +
                    "PID~F~~~~" + linList.get(1)[2] + "\n" +
                    "MEA~DE~TT~0~MO\n" +
                    "LDT~AF~" + linList.get(0)[2] + "~DW\n" +
                    "CTP~~D01~" + formatNumber(Double.parseDouble(linList.get(0)[3]), linList.get(0)[3].length())+ "~1~EA");
            totalCount = totalCount+6;
        }


    }

    /**
     * N1~DL~BCol
     * N3~CCol~DCol
     * N4~Ecolumn~Fcolumn~Gcolumn
     * PER~OD~Hcol~TE~Icol~FX~Jcol~EM~Kcol
     **/
    private static void sevenN(String[] string) {

        String N3 = "";
        if (string[3] != null) {
            N3 = "N3~" + string[2] + "~" + string[3] + "\n";
        } else {
            N3 = "N3~" + string[2] + "\n";
        }
        appendToFile(
                "N1~DL~" + string[1] + "\n" +
                        N3 +
                        "N4~" + string[4] + "~" + string[5] + "~" + string[6] + "\n" +
                        "PER~OD~" + string[7] + "~TE~" + string[8] + "~FX~" + string[9] +
                        "~EM~" + string[10]);
        totalCount = totalCount+4;
    }


    /**
     * ISA~00~          ~00~          ~16~932704765EDIG  ~ZZ~GSAADV         ~Hcol~1358~U~00300~000000073~0~P~^
     * GS~SC~932704765EDIG~012966339~FCol~1358~73~X~003050
     * ST~832~0001
     */
    private static void ISA(String[] string) {

        appendToFile("ISA~00~          " +
                "~00~          " +
                "~16~932704765EDIG  " +
                "~ZZ~GSAADV         ~" +
                string[5] +
                "~1358~U~00300~000000073~0~P~^");
        appendToFile("GS~SC~932704765EDIG~012966339~" +
                string[5] +
                "~1358~73~X~003050");
        appendToFile("ST~832~0001");
        totalCount=totalCount+3;
    }


    /**
     * BCT~CP~concat(H Col,I Col)~~~~concat(H Col,J Col)~~~~ACol where it is 05 for the first line
     */
    private static void BCT_CP(String[] string) {
        appendToFile("BCT~CP~" +
                BCP[7] + BCP[8] +
                "~~~~" +
                BCP[7] + BCP[9] +
                "~~~~" +
                string[0]);

        REF(BCP);
        DTM(BCP);
        ctbList.add(BCP);
        BCP = null;
        totalCount++;
    }


    /**
     * REF~GC~H Col(Value Is = GS-35F-0156V)
     * REF~W6~K Col(Value Is = 70)
     * REF~ME~~S Col (Value = https://news.avaya.com/gov-gsa-schedule)
     * REF~92~P Col(Value = 123)
     * REF~TN~~U Col(Value = GS35F0156V_TCs.pdf)
     */
    private static void REF(String[] string) {

        appendToFile("REF~GC~" + string[7] + "\n" +
                "REF~W6~" + string[10] + "\n" +
                "REF~ME~~" + string[18] + "\n" +
                "REF~92~" + string[15] + "\n" +
                "REF~TN~~" + string[20]
        );

        totalCount=totalCount+5;

    }

    /**
     * DTM~007~SubString(Ecol,3,9)~~~substring(E Col,0,2) 🡪Sample🡪 DTM~007~190425~~~2
     * DTM~036~SubString(Ecol,3,9)~~~substring(E Col,0,2) 🡪Sample🡪 DTM~036~991231~~~99
     */
    private static void DTM(String[] string) {
        appendToFile("DTM~007~" +
                string[4].substring(2) +
                "~~~" +
                string[4].substring(0, 2));
        appendToFile("DTM~036~" +
                string[5].substring(2) +
                "~~~" +
                string[5].substring(0, 2));
        totalCount=totalCount+2;
    }


    /**
     * CTB~OR~~~~MO~Div by 100(R Col)
     * CTB~OR~132-3,Concat(Col B3,Col B4),Concat(Col B8, Col B9)132-53,Col B10132-33~~~MA~ Mul by 100(R Col)
     * CTB~OR~132-50~~~MA~Mul by 100(Col C7)
     * CTB~OR~132-34~~~MA~ Mul by 100(Col C8)
     * ITD~~~~~0~~30~~~~~~~N🡪Complete Line HardCode
     * FOB~PS~DE🡪Complete Line HardCode
     * <p>
     * ############################################################
     * N1~40~~1~012966339
     * N1~SE~Col C11.~1~Col B11
     * N3~Col W2
     * N4~Col Y2~VA~Col AA2
     * REF~4A~Col L2
     * PER~OD~Col B12~TE~Col C12~FX~Col D12~EM~Col E12
     * PER~AC~Col F12~TE~Col G12~FX~Col H12~EM~Col I12
     * PER~1A~Col J12~TE~Col K12~FX~Col L12~EM~Col M12
     * N1~RI~Col C11~1~Col B11
     * N2~Col D11
     * N3~Col F11 ~Concat(Col G11,H11)
     * N4~Col J11~Col K11~Col L11
     */
    private static void CTB(String[] string, boolean computeCtb) {

        if (!computeCtb) {
            ctbList.add(string);
        } else {

            appendToFile("CTB~OR~~~~MO~" + (int) (Double.parseDouble(ctbList.get(0)[16]) * 100) + "`" +
                    "CTB~OR~132-3," +
                    ctbList.get(1)[1] + "," +
                    ctbList.get(2)[1] + "," +
                    ctbList.get(6)[1] + "," +
                    ctbList.get(7)[1] + "," +
                    "132-53" + "," +
                    ctbList.get(8)[1] + "," +
                    ctbList.get(3)[1] +
                    "~~~MA~" +
                    +(int) (Double.parseDouble(ctbList.get(0)[17]) * 100) + "\n" +
                    "CTB~OR~" +
                    ctbList.get(5)[1]
                    +
                    "~~~MA~" +
                    +(int) (Double.parseDouble(ctbList.get(5)[2]) * 100) +
                    "\n" +
                    "CTB~OR~" +
                    ctbList.get(4)[1] +
                    "~~~MA~" +
                    +(int) (Double.parseDouble(ctbList.get(4)[2]) * 100) +
                    "\n" +
                    "ITD~~~~~0~~30~~~~~~~N\n" +
                    "FOB~PS~DE");
            appendToFile("N1~40~~1~012966339\n" +
                    "N1~SE~" +
                    string[2] +
                    "~1~" +
                    string[1] + "\n" +
                    "N3~" + ctbList.get(0)[22] + "\n" +
                    "N4~" + ctbList.get(0)[24] + "~VA~" + ctbList.get(0)[26] + "\n" +
                    "REF~4A~" + ctbList.get(0)[11]);

            PER = string;
            ctbList.clear();
            totalCount=totalCount+18;
        }

    }

    /**
     * PER~OD~BCol~TE~CCol~FX~DCol~EM~Ecol
     * PER~AC~Fcol~TE~Gcol~FX~Hcol~EM~Icol
     * PER~1A~Jcol~TE~kcol~FX~Lcol~EM~Mcol
     * N1~RI~Ccol~1~Bcol
     * N2~Dcol
     * N3~FCol~Gcol
     * N4~JCol~KCol~LCol
     */
    private static void sixN(String[] string) {

        String per11=PER[11];
        if(PER[11].length() > 9)
        {
            per11=PER[11].substring(0,9);
        }
        appendToFile(
                "PER~OD~" + string[1] + "~TE~" + string[2] + "~FX~" + string[3] + "~EM~" + string[4] + "\n" +
                        "PER~AC~" + string[5] + "~TE~" + string[6] + "~FX~" + string[7] + "~EM~" + string[8] + "\n" +
                        "PER~1A~" + string[9] + "~TE~" + string[10] + "~FX~" + string[11] + "~EM~" + string[12] + "\n" +
                        "N1~RI~" + PER[2] + "~1~" + PER[3] + "\n" +
                        "N2~" + PER[3] + "\n" +
                        "N3~" + PER[5] + "~" + PER[6] + "\n" +
                        "N4~" + PER[9] + "~" + PER[10] + "~" + PER[11]);
    }

    /**
     * CTT~(LINCount)
     * SE~350907~0001
     * GE~1~73
     * IEA~1~000000073
     */
    private static void tail() {
        appendToFile("CTT~"+sequence+"\n" +
                "SE~350907~0001\n" +
                "GE~1~73\n" +
                "IEA~1~000000073\n");
        System.out.println(totalCount);
    }

     static void appendToFile(String string) {
        App.out.print(string.replaceAll("\\r", "")+"\n");
        App.out.flush();
        //System.out.println(System.getProperty("line.separator"));
    }

    private static String formatNumber(Double d, int length)
    {
        df.setMaximumFractionDigits(length);
        return df.format(d);
    }
}
