from flask import Flask
import json




app = Flask(__name__)

@app.route('/')
def index():
    return json.dumps({'hello': 'world'})

@app.route('/medicine/load-medicine-web',methods=['OPTIONS','POST'])
def getMedicineList(self):
        return json.dumps({
            'status':"SUCCESS",
            'data':[
                {
                    "item_name":"Crocin",
                    "id":"001"
                },

                {
                    "item_name": "Paracetomol",
                    "id": "002"
                }
            ]
        })


if __name__ == '__main__':
    app.run('0.0.0.0',8745,debug=True)