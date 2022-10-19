from keras.models import Sequential
from keras.layers import Dense, Conv2D, Flatten, MaxPooling2D
import numpy as np
from PIL import Image
import glob
import numpy as np

def load_data():
    inputs = []
    outputs=[]

    for photo in glob.glob('data/sad/*.jpg'):
        im = Image.open(photo).resize((50,50))
        inputs.append(np.asarray(im))
        outputs.append(np.array([0,1]))

    for photo in glob.glob('data/happy/*.jpg'):
        im = Image.open(photo).resize((50,50))
        inputs.append(np.asarray(im))
        outputs.append(np.array([1,0]))

    permutation = np.random.permutation(len(inputs))
    inputs = np.asarray(inputs)[permutation]
    outputs = np.asarray(outputs)[permutation]
    return inputs, outputs


def splitData(inputs, outputs):
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = np.asarray([inputs[i] for i in trainSample])
    trainOutputs =  np.asarray([outputs[i] for i in trainSample])
    testInputs =  np.asarray([inputs[i] for i in testSample])
    testOutputs = np.asarray([outputs[i] for i in testSample])
    return trainInputs, trainOutputs, testInputs, testOutputs

inputs, outputs=load_data()
trainInputs, trainOutputs, testInputs, testOutputs = splitData(inputs, outputs)

model = Sequential()
model.add(Conv2D(32,kernel_size=3, input_shape=(50,50,3),activation='relu'))
model.add(Conv2D(32,kernel_size=3, activation='relu'))
model.add(MaxPooling2D(pool_size=(2,2)))

model.add(Conv2D(32,kernel_size=3, input_shape=(50,50,3),activation='relu'))
model.add(Conv2D(32,kernel_size=3, activation='relu'))
model.add(MaxPooling2D(pool_size=(2,2)))

model.add(Flatten())
model.add(Dense(2, activation='softmax'))
model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])
model.fit(trainInputs, trainOutputs, epochs=10)
model.predict(testInputs)