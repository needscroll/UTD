
def getPrediction(trainingData, i):
    neighbors = []
    point = trainingData[i]

    if i == 0:
        neighbors.append(trainingData[i + 1])
    elif i == (len(trainingData) - 1):
        neighbors.append(trainingData[i - 1])
    else:
        neighbors.append(trainingData[i - 1])
        neighbors.append(trainingData[i + 1])

    if len(neighbors) == 1:
        return(neighbors[0])
    else:
        left = neighbors[0]
        right = neighbors[1]
        if left[0] < right[0]:
            return left
        elif right[0] < left[0]:
            return right
        elif right[0] == left[0]:
            if not right[1] == point[1]:
                return right
            else:
                return left


    return neighbors

def calcPredictions(trainingData):
    closestPoint = []
    predictedClassification = []
    for i in range(len(trainingData)):
        closestPoint.append(getPrediction(trainingData, i))

    for x in closestPoint:
        predictedClassification.append(x[1])
    return closestPoint, predictedClassification


def train(data, i):
    x = data[i]
    trainingData = data.copy()
    trainingData.remove(x)
    closestpoint, predictions = calcPredictions(trainingData)

    for j in range(len(trainingData)):
        trainingData[j][1] = predictions[j]

    print("DEBUG:i:" + str(i))
    print("DEBUG:x:" + str(x))
    print("DEBUG:Training:" + str(trainingData))
    combinedData = []
    for k in range(i):
        combinedData.append(trainingData[k])
    combinedData.append(x)
    for k in range(i, len(trainingData)):
        combinedData.append(trainingData[k])
    print("DEBUG:Combined:" + str(combinedData))

    singlePrediction = getPrediction(combinedData, i)
    print("DEBUG:Prediction:" + str(singlePrediction))
    return singlePrediction[1]


print("Hello Lain")
data = [[0, 0], [1, 0], [2, 0], [3, 0],
        [4, 1], [5, 0], [6, 0], [7, 0],
        [8, 0], [9, 1], [10, 1], [11, 1],
        [12, 1], [13, 0], [14, 1], [15, 1],
        [16, 1], [17, 1]]

correct = 0
for i in range(len(data)):
    prediction = train(data, i)
    if prediction == data[i][1]:
        correct += 1
print("DEBUG: Accuracy:" + str((correct / len(data))))
