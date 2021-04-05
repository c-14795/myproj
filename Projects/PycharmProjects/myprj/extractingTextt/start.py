from wand.image import Image
from PIL import Image as PI
import pyocr
import pyocr.builders
import io


tool = pyocr.get_available_tools()
print tool

lang = tool[0].get_available_languages()[1]
print lang

req_image = []
final_text = []

image_pdf = Image(filename="/home/c14795/Documents/CIOMS Sample - 4.pdf", resolution=300)
image_jpeg = image_pdf.convert('jpeg')

image_jpeg.save(filename="/home/c14795/PycharmProjects/myprj/resources/CIOMS SAMPLE - 4.jpeg")

# for img in image_jpeg.sequence:
#     img_page = Image(image=img)
#     req_image.append(img_page.make_blob('jpeg'))
#
#
# for img in req_image:
#     txt = tool[0].image_to_string(
#         PI.open(io.BytesIO(img)),
#         lang=lang,
#         builder=pyocr.builders.TextBuilder()
#     )
#     final_text.append(txt)
#
#
# print final_text

