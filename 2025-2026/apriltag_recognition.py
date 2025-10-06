import cv2, os
from pupil_apriltags import Detector

detector = Detector(families="tag36h11")
cap = cv2.VideoCapture(0)

recognizer = 20

while True:
    ret, frame = cap.read()
    if not ret:
        break

    h, w, _ = frame.shape

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    tags = detector.detect(gray)

    if len(tags) >= 1:

        for tag in tags:
            if int(tag.tag_id) == int(recognizer):
                (ptA, ptB, ptC, ptD) = tag.corners
                ptA = (int(ptA[0]), int(ptA[1]))
                ptB = (int(ptB[0]), int(ptB[1]))
                ptC = (int(ptC[0]), int(ptC[1]))
                ptD = (int(ptD[0]), int(ptD[1]))

                cv2.line(frame, ptA, ptB, (0, 255, 0), 2)
                cv2.line(frame, ptB, ptC, (0, 255, 0), 2)
                cv2.line(frame, ptC, ptD, (0, 255, 0), 2)
                cv2.line(frame, ptD, ptA, (0, 255, 0), 2)

                cX, cY = int(tag.center[0]), int(tag.center[1])
                cv2.circle(frame, (cX, cY), 5, (0, 0, 255), -1)

                start_point = (0, cY)
                end_point = (w, cY)
                color = (0, 0, 255)
                thickness = 1
                cv2.line(frame, start_point, end_point, color, thickness)

                start_point = (cX, 0)
                end_point = (cX, h)
                color = (0, 0, 255)
                thickness = 1
                cv2.line(frame, start_point, end_point, color, thickness)

                dxl = (w/2)-cX
                dyl = (h/2)-cY

                os.system("cls")
                print(f"Move turret {-dxl} horizontal, {-dyl} vertical")
                
                cv2.putText(frame, "Tag ID: " + str(tag.tag_id),
                            (ptA[0], ptA[1] - 10),
                            cv2.FONT_HERSHEY_SIMPLEX, 0.5,
                            (255, 0, 0), 2)
            else:
                print(f"Tag detected but not recognized", end="\r")
    else:
        print("No tag detected", end="\r")
    
    cv2.imshow("AprilTag Detection", frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()