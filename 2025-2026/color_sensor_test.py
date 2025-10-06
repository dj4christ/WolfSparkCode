import cv2, os
import numpy as np

t = 30
t2 = 20
m = 127
rp, gp, bp = 155, 100, 209
rg, gg, bg = 60, 176, 67

cap = cv2.VideoCapture(0)

while True:
    ret, img = cap.read()
    if not ret:
        break

    ab, ag, ar, _ = cv2.mean(img)

    h, w, _ = img.shape
    print(w, h)
    b, g, r= img[round(h/2), round(w/2)]

    b, g, r = round(b), round(g), round(r)

    color = (b, g, r)
    solid_img = np.full((h, w, 3), color, dtype=np.uint8)
    cv2.imshow("Middle Color", solid_img)

    center_y, center_x = h // 2, w // 2
    img[center_y, center_x] = (0, 0, 255)
    cv2.rectangle(img, (w//2-5, h//2-5), (w//2+5, h//2+5), (0, 255, 0), 1)
    cv2.imshow("Preview", img)

    os.system("cls")
    print(f"{r} {g} {b}", end=" ")

    if g > r+t and g > b+t:
        print("green")
    elif r > g+t and r > b+t:
        print("red")
    elif b > r+t and b > g+t:
        print("blue")
    else:
        print("none")
    
    if (r <= rp+t2 and r >= rp-t2) and (g <= gp+t2 and g >= gp-t2) and (b <= bp+t2 and b >= bp-t2):
        print("Purple")
        break
    elif (r <= rg+t2 and r >= rg-t2) and (g <= gg+t2 and g >= gg-t2) and (b <= bg+t2 and b >= bg-t2):
        print("Green")
        break

    # -250 230

    # if ab >= m:
    #     b = round(b-((ab-b)/2))
    # else:
    #     b = round(b+((b-ab)/2))
    
    # if ar >= m:
    #     r = round(r-((ar-r)/2))
    # else:
    #     r = round(r+((r-ar)/2))
    
    # if ag >= m:
    #     g = round(g-(ag-g)/2)
    # else:
    #     g = round(g+(g-ag)/2)
    
    # color = (b, g, r)
    # solid_img = np.full((height, width, 3), color, dtype=np.uint8)
    # cv2.imshow("Actual Color", solid_img)

    # color = (ab, ag, ar)
    # solid_img = np.full((height, width, 3), color, dtype=np.uint8)
    # cv2.imshow("Lighting", solid_img)

    if cv2.waitKey(1) & 0xFF == ord("q"):
        break