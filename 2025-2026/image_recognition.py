import cv2
from pupil_apriltags import Detector

detector = Detector(families="tag36h11")

cap = cv2.VideoCapture(0)

while True:
    ret, frame = cap.read()
    if not ret:
        break

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    tags = detector.detect(gray)

    for tag in tags:
        (ptA, ptB, ptC, ptD) = tag.corners  # 4 corners
        ptA = (int(ptA[0]), int(ptA[1]))
        ptB = (int(ptB[0]), int(ptB[1]))
        ptC = (int(ptC[0]), int(ptC[1]))
        ptD = (int(ptD[0]), int(ptD[1]))

        # Draw the outline of the tag
        cv2.line(frame, ptA, ptB, (0, 255, 0), 2)
        cv2.line(frame, ptB, ptC, (0, 255, 0), 2)
        cv2.line(frame, ptC, ptD, (0, 255, 0), 2)
        cv2.line(frame, ptD, ptA, (0, 255, 0), 2)

        # Draw center point
        cX, cY = int(tag.center[0]), int(tag.center[1])
        cv2.circle(frame, (cX, cY), 5, (0, 0, 255), -1)

        # Put tag ID text
        cv2.putText(frame, "Tag ID: "+str(tag.tag_id),
                    (ptA[0], ptA[1] - 10),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.5,
                    (255, 0, 0), 2)

    # 6. Show frame
    cv2.imshow("AprilTag Detection", frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()