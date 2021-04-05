import cv2
import numpy as np
from matplotlib import pyplot as plt

img_rgb = cv2.imread('/home/c14795/PycharmProjects/myprj/resources/CIOMS SAMPLE - 1-0.jpeg')
img_gray = cv2.cvtColor(img_rgb, cv2.COLOR_BGR2GRAY)
template = cv2.imread('/home/c14795/PycharmProjects/myprj/resources/CIOMS SAMPLE - 1-0 cropped.jpeg',0)
w, h = template.shape[::-1]

res = cv2.matchTemplate(img_gray,template,cv2.TM_CCOEFF_NORMED)
threshold = 0.8
#print res
for i in res:
    print i

loc = np.where( res >= threshold)
print loc
for pt in zip(*loc[::-1]):
    cv2.rectangle(img_rgb, pt, (pt[0] + w, pt[1] + h), (0,0,255), 2)

cv2.imwrite('res1.png',img_rgb)