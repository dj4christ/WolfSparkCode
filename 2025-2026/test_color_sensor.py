import cv2

width, height = 1024, 720

cap = cv2.VideoCapture(0)

count = 0
average_r = 150
average_g = 150
average_b = 150

while True:
    ret, frame = cap.read()
    if not ret:
        break

    img = cv2.imread(frame)

    for i in range(0, img.shape[0]):
        for j in range(0, img.shape[1]):
            b, g, r = img[i, j]
            print(b, g, r)
