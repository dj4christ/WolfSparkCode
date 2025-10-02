# import cv2

# t = 10

# # width, height = 1024, 720

# rp, gp, bp = 155, 43, 209
# rg, gg, bg = 60, 176, 67
# width, height = 640, 480  # use safe default
# cap = cv2.VideoCapture(0)

# # Check if camera opened successfully
# if not cap.isOpened():
#     print("Error: Could not open camera")
#     exit()

# cap.set(cv2.CAP_PROP_FRAME_WIDTH, width)
# cap.set(cv2.CAP_PROP_FRAME_HEIGHT, height)

# while True:
#     ret, img = cap.read()
#     if not ret:
#         print("Failed to capture frame")
#         break

#     cv2.imshow("preview", img)

#     if cv2.waitKey(1) & 0xFF == ord("q"):
#         break
import cv2

t = 2
rp, gp, bp = 155, 43, 209
rg, gg, bg = 60, 176, 67

width, height = 640, 480  # use safe default

cap = cv2.VideoCapture(2)

while True:
    ar, ag, ab = 127, 127, 127
    count = 0
    ret, img = cap.read()
    if not ret:
        break

    cv2.imshow("preview", img)

    ab, ag, ar, _ = cv2.mean(img)

    # for i in range(0, img.shape[0]):
    #     for j in range(0, img.shape[1]):
    #         b, g, r = img[i, j]

    #         count += 1
    #         ar += r
    #         ag += g
    #         ab += b

    
    b, g, r= img[round(height/2), round(width/2)]
    b, g, r = round(b-ab), round(g-ag), round(r-ar)
    print(r, g, b)

    if (r >= ar-t and r <= ar+t) and (g >= ag-t and g <= ag+t) and (b >= ab-t and b <= ab+t):
        print('Color recognized: purple')
    
    

    if cv2.waitKey(1) & 0xFF == ord("q"):
        break