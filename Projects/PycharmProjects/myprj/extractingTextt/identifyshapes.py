import numpy as np
import cv2

image = cv2.imread("/home/c14795/PycharmProjects/myprj/resources/CIOMS SAMPLE - 1-0.jpeg")

lower = np.array([0, 0, 0])
upper = np.array([15, 15, 15])

shapeMask = cv2.inRange(image, lower, upper)

# find the contours in the mask
(cnts, _,_q) = cv2.findContours(shapeMask.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
print "I found %d black shapes" % (len(cnts))
print len(_)
print len(_q)

cv2.imshow("mm", shapeMask)
cv2.waitKey(0)

# epsilon =  cv2.arcLength(_[0],True)
# approx = cv2.approxPolyDP(_[0],epsilon,True)
#
# print epsilon
# print  approx
#
# for c in _:
# 	# draw the contour and show it
# 	cv2.drawContours(image, [c], -1, (0, 255, 0), 2)
# 	cv2.imshow("Image", image)
# 	cv2.waitKey(0)

