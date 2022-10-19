from sklearn import neural_network
from PIL import Image
import glob
import numpy as np
from sklearn.preprocessing import StandardScaler
import matplotlib.pyplot as plt

# function to split data into training and testing data
def splitData(inputData, outputData):
    np.random.seed(5)
    indexes = [i for i in range(len(inputData))]
    trainingS = np.random.choice(indexes, int(0.8 * len(inputData)), replace=False)
    testingS = [i for i in indexes if i not in trainingS]

    trainingInputs = []
    trainingOutputs = []
    testingInputs = []
    testingOutputs = []
    for i in trainingS:
        trainingInputs.append(inputData[i])
    for i in trainingS:
        trainingOutputs.append(outputData[i])
    for i in testingS:
        testingInputs.append(inputData[i])
    for i in testingS:
        testingOutputs.append(outputData[i])
    return trainingInputs, trainingOutputs, testingInputs, testingOutputs

# function to read data
def readData():
    inputPhotos = []
    outputPhotos = []

    for photo in glob.glob('nnn/*.jpg'):
        image = Image.open(photo)
        resizedImage = image.resize((10, 10))
        resizedImage = np.asarray(resizedImage)
        inputPhotos.append(resizedImage)
        outputPhotos.append(0)

    for photo in glob.glob('sss/*.jpg'):
        image = Image.open(photo)
        resizedImage = image.resize((10, 10))
        resizedImage = np.asarray(resizedImage)
        inputPhotos.append(resizedImage)
        outputPhotos.append(1)

    permutation = np.random.permutation(len(inputPhotos))
    inputPhotos = np.asarray(inputPhotos)[permutation]
    outputPhotos = np.asarray(outputPhotos)[permutation]

    return inputPhotos, outputPhotos

def normalisation(trainingData, testingData):
    myScaler = StandardScaler()
    if not isinstance(trainingData[0], list):
        trainingData = [[x] for x in trainingData]
        testingData = [[x] for x in testingData]

        myScaler.fit(trainingData)
        normalisedTrainingData = myScaler.transform(trainingData)
        normalisedTestingData = myScaler.transform(testingData)

        a = []
        for x in normalisedTrainingData:
            a.append(x[0])
        normalisedTrainingData = a

        b = []
        for x in normalisedTestingData:
            b.append(x[0])
        normalisedTestingData = b
    else:
        myScaler.fit(trainingData)
        normalisedTrainingData = myScaler.transform(trainingData)
        normalisedTestingData = myScaler.transform(testingData)
    return normalisedTrainingData, normalisedTestingData

def detConfusionMatrix(realLabels, computedLabels, labels):
    confusionMatrix = []
    for i in range(len(labels)):
        a = []
        for j in range(len(labels)):
            a.append(0)
        confusionMatrix.append(a)

    for i in range(len(computedLabels)):
        actual = 0
        predicted = 0
        for j in range(len(labels)):
            if labels[j] == realLabels[i]:
                actual = j
            if labels[j] == computedLabels[i]:
                predicted = j
        confusionMatrix[actual][predicted] = confusionMatrix[actual][predicted] + 1
    return confusionMatrix


def evalFunction(realData, computedData, labels):
    precision = []
    recall = []
    for i in range(len(labels)):
        precision.append(0.0)
    for i in range(len(labels)):
        recall.append(0.0)
    confusionMatrix = detConfusionMatrix(realData, computedData, labels)

    accuracy = sum(confusionMatrix[i][i] for i in range(len(labels))) / len(realData)

    for i in range(len(labels)):
        predictedPossibility = 0.0
        for j in range(len(labels)):
            predictedPossibility = predictedPossibility + confusionMatrix[j][i]
        precision[i] = confusionMatrix[i][i] / predictedPossibility

    for i in range(len(labels)):
        actualPossibility = 0.0
        for j in range(len(labels)):
            actualPossibility = actualPossibility + confusionMatrix[i][j]
        recall[i] = confusionMatrix[i][i] / actualPossibility
    return accuracy, precision, recall

# reading the data as inputData and outputData
inputData, outputData = readData()

# splitting the data into training and testing
trainingInput, trainingOutput, testingInput, testingOutput = splitData(inputData, outputData)

trainingInputX = []
xx = []
for x in trainingInput:
    for l in x:
        for elem in l:
            for e in elem:
                xx.append(e)
    trainingInputX.append(xx)
    xx = []

testingInputX = []
yy = []
for x in testingInput:
    for l in x:
        for elem in l:
            for e in elem:
                yy.append(e)
    testingInputX.append(yy)
    yy = []

trainingInputX, testingInputX = normalisation(trainingInputX, testingInputX)

classifier = neural_network.MLPClassifier(hidden_layer_sizes=(5,), activation='relu', max_iter=3000, solver='sgd', verbose=10, random_state=1, learning_rate_init=0.001)

classifier.fit(trainingInputX, trainingOutput)
loss_values = classifier.loss_curve_
plt.plot(loss_values)
plt.show()

predictedLabels = classifier.predict(testingInputX)

accuracy, precision, recall = evalFunction(testingOutput, predictedLabels, [0, 1])
print("accuracy:", accuracy * 100)
print("precision:", precision)
print("recall", recall)