import math

import numpy
import numpy as np
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split

def f_true(x):
    y = 6.0 * (np.sin(x + 2) + np.sin(2*x + 4))
    return y

# X float(n, ): univariate data
# d int: degree of polynomial
def polynomial_transform(X, d):
    resultingArray = []
    for x in X:
        row = [1]
        for i in range(d):
            i += 1
            xValue = math.pow(x, i)
            row.append(xValue)
        resultingArray.append(row)
    return np.array(resultingArray)

# Phi float(n, d): transformed data
# y float(n, ): labels
def train_model(Phi, y):
    #print("DEBUG: Training")
    result = Phi.transpose()@Phi
    result = np.linalg.inv(result)
    result = result@Phi.transpose()
    result = result * y
    return result

# Phi float(n, d): transformed data
# y float(n, ): labels
# w float(d, ): linear regression model
def evaluate_model(Phi, y, w):
    #print("DEBUG: Evaluating")
    sum = 0
    for i in range(len(Phi)):
        err = (y[i] - w.transpose()@Phi[i])**2
        sum += err

    return sum / len(Phi)

# X float(n, ): univariate data
# B float(n, ): basis functions
# gamma float : standard deviation / scaling of radial basis kernel
def radial_basis_transform(X, B, gamma=0.1):
    resultingArray = []
    for x1 in X:
        row = []
        for x2 in B:
            exponent = -1 * gamma * ((x1 - x2)**2)
            result = math.pow(math.e, exponent)
            row.append(result)
        resultingArray.append(row)
    return np.array(resultingArray)

# Phi float(n, d): transformed data
# y float(n, ): labels
# lam float : regularization parameter
def train_ridge_model(Phi, y, lam):
    return np.linalg.inv(Phi.transpose()@Phi + lam*np.identity(len(Phi)))@Phi.transpose()@y

def debug():
    print("DEBUG:Hello Lain")

    arr = [1, 2, 3, 4]
    labels = [10, 11, 12, 13]
    vanMatrix = polynomial_transform(arr, 3)
    print("DEBUG: Van Matrix:\n" + str(vanMatrix))
    trainResult = train_model(vanMatrix, labels)
    print("DEBUG:Train Result:" + str(trainResult))
    error = evaluate_model(vanMatrix, labels, trainResult)
    print("DEBUG:Error:" + str(error))



    print("DEBUG:Goodbye Lain")

def Q1PartD():
    print("Hello Lain")
    print("DEBUG: Q1PartD")

    # SETUP
    n = 750  # Number of data points
    X = np.random.uniform(-7.5, 7.5, n)  # Training examples, in one dimension
    e = np.random.normal(0.0, 5.0, n)  # Random Gaussian noise
    y = f_true(X) + e # True labels with noise

    plt.figure()
    # Plot the data
    plt.scatter(X, y, 12, marker='o')
    # Plot the true function, which is really "unknown"
    x_true = np.arange(-7.5, 7.5, 0.05)
    y_true = f_true(x_true)
    plt.plot(x_true, y_true, marker='None', color='r')

    tst_frac = 0.3  # Fraction of examples to sample for the test set
    val_frac = 0.1  # Fraction of examples to sample for the validation set
    # First, we use train_test_split to partition (X, y) into training and test sets
    X_trn, X_tst, y_trn, y_tst = train_test_split(X, y, test_size=tst_frac, random_state = 42)
    # Next, we use train_test_split to further partition (X_trn, y_trn) into training and validationsets
    X_trn, X_val, y_trn, y_val = train_test_split(X_trn, y_trn, test_size=val_frac , random_state=42)

    plt.figure()
    plt.scatter(X_trn, y_trn, 12, marker='o', color='orange')
    plt.scatter(X_val, y_val, 12, marker='o', color='green')
    plt.scatter(X_tst, y_tst, 12, marker='o', color='blue')

    w = {}  # Dictionary to store all the trained models
    validationErr = {}  # Validation error of the models
    testErr = {}  # Test error of all the models

    for d in range(3, 25, 3):
        Phi_trn = polynomial_transform(X_trn, d)
        w[d] = train_model(Phi_trn, y_trn)
        #print(str(len(w[d])))
        Phi_val = polynomial_transform(X_val, d)
        validationErr[d] = evaluate_model(Phi_val, y_val, w[d])
        Phi_tst = polynomial_transform(X_tst, d)
        testErr[d] = evaluate_model(Phi_tst, y_tst, w[d])
        #print(str(testErr[d]))

    plt.figure()
    plt.plot(validationErr.keys(), validationErr.values(), marker='o', linewidth=3, markersize=12)
    plt.plot(testErr.keys(), testErr.values(), marker='s', linewidth=3, markersize=12)
    plt.xlabel('Polynomial degree', fontsize=16)
    plt.ylabel('Validation/Test error', fontsize=16)
    plt.xticks(list(validationErr.keys()), fontsize=12)
    plt.legend(['Validation Error', 'Test Error'], fontsize=16)
    plt.axis([2, 25, 15, 60])

    plt.figure()
    plt.plot(x_true, y_true, marker='None', linewidth=5, color='k')
    
    for d in range(9, 25, 3):
        X_d = polynomial_transform(x_true, d)
        y_d = X_d @ w[d]
        plt.plot(x_true, y_d, marker='None', linewidth=2)

    plt.legend(['true'] + list(range(9, 25, 3)))
    plt.axis([-8, 8, -15, 15])
    plt.show()

    print("Goodbye Lain")

def Q2PartC():
    print("Hello Lain")
    print("DEBUG: Q2PartC")

    # SETUP
    n = 750  # Number of data points
    X = np.random.uniform(-7.5, 7.5, n)  # Training examples, in one dimension
    e = np.random.normal(0.0, 5.0, n)  # Random Gaussian noise
    y = f_true(X) + e # True labels with noise

    plt.figure()
    # Plot the data
    plt.scatter(X, y, 12, marker='o')
    # Plot the true function, which is really "unknown"
    x_true = np.arange(-7.5, 7.5, 0.05)
    y_true = f_true(x_true)
    plt.plot(x_true, y_true, marker='None', color='r')

    tst_frac = 0.3  # Fraction of examples to sample for the test set
    val_frac = 0.1  # Fraction of examples to sample for the validation set
    # First, we use train_test_split to partition (X, y) into training and test sets
    X_trn, X_tst, y_trn, y_tst = train_test_split(X, y, test_size=tst_frac, random_state = 42)
    # Next, we use train_test_split to further partition (X_trn, y_trn) into training and validationsets
    X_trn, X_val, y_trn, y_val = train_test_split(X_trn, y_trn, test_size=val_frac , random_state=42)

    plt.figure()
    plt.scatter(X_trn, y_trn, 12, marker='o', color='orange')
    plt.scatter(X_val, y_val, 12, marker='o', color='green')
    plt.scatter(X_tst, y_tst, 12, marker='o', color='blue')

    w = {}  # Dictionary to store all the trained models
    validationErr = {}  # Validation error of the models
    testErr = {}  # Test error of all the models

    lambdaValues = []
    for i in range(-3, 4):
        lambdaValues.append(math.pow(10, i))

    for i in range(len(lambdaValues)):
        Phi_trn = radial_basis_transform(X_trn, X_trn)
        w[i] = train_ridge_model(Phi_trn, y_trn, i)

        Phi_val = radial_basis_transform(X_val, X_trn)
        validationErr[i] = evaluate_model(Phi_val, y_val, w[i])

        Phi_tst = radial_basis_transform(X_tst, X_trn)
        testErr[i] = evaluate_model(Phi_tst, y_tst, w[i])

    plt.figure()
    plt.plot(validationErr.keys(), validationErr.values(), marker='o', linewidth=3, markersize=12)
    plt.plot(testErr.keys(), testErr.values(), marker='s', linewidth=3, markersize=12)
    plt.xlabel('lambda values', fontsize=16)
    plt.ylabel('Validation/Test error', fontsize=16)
    plt.xticks(list(validationErr.keys()), fontsize=12)
    plt.legend(['Validation Error', 'Test Error'], fontsize=16)
    plt.axis([2, 25, 15, 60])

    plt.figure()
    plt.plot(x_true, y_true, marker='None', linewidth=5, color='k')

    for i in range(len(lambdaValues)):
        X_d = radial_basis_transform(x_true, x_true)
        wCopy = w[i][:len(X_d)]
        y_d = X_d @ wCopy
        plt.plot(x_true, y_d, marker='None', linewidth=2)

    plt.legend(['true'] + list(lambdaValues))
    plt.axis([-8, 8, -15, 15])
    plt.show()

    print("Goodbye Lain")

Q1PartD()
Q2PartC()