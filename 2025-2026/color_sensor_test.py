import cv2
import numpy as np

t = 50
m = 127
rp, gp, bp = 155, 43, 209
rg, gg, bg = 60, 176, 67

width, height = 640, 480  # use safe default


cap = cv2.VideoCapture(0)

while True:
    ar, ag, ab = 127, 127, 127
    count = 0
    ret, img = cap.read()
    if not ret:
        break

    ab, ag, ar, _ = cv2.mean(img)

    # for i in range(0, img.shape[0]):
    #     for j in range(0, img.shape[1]):
    #         b, g, r = img[i, j]

    #         count += 1
    #         ar += r
    #         ag += g
    #         ab += b

    b, g, r= img[round(height/2), round(width/2)]
    
    color = (r, g, b)
    solid_img = np.full((height, width, 3), color, dtype=np.uint8)
    cv2.imshow("Middle Color", solid_img)

    # print(f"{r} {g} {b}", end="\r")

    # if ab >= m:
    #     b = round(b-(ab-m))
    # else:
    #     b = round(b+(m+ab))
    
    # if ar >= m:
    #     r = round(r-(ar-m))
    # else:
    #     r = round(r+(m+ar))
    
    # if ag >= m:
    #     g = round(g-(ag-m))
    # else:
    #     g = round(g+(m+ag))


    # color = (ar, ag, ab)
    # solid_img = np.full((height, width, 3), color, dtype=np.uint8)
    # cv2.imshow("Lighting", solid_img)

    center_y, center_x = height // 2, width // 2
    img[center_y, center_x] = (0, 0, 255)
    cv2.imshow("preview", img)

    if g > r-t and g > b-t:
        print("green")
    elif r > g-t and r > b-t:
        print("red")
    elif b > r-t and b > g-t:
        print("blue")
    else:
        print("none")
    

    if cv2.waitKey(1) & 0xFF == ord("q"):
        break