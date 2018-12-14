#!/usr/bin/python
# -*- coding: utf-8 -*-
# ----------------------------------------------
# --- Author         : Ahmet Ozlu
# --- Mail           : ahmetozlu93@gmail.com
# --- Date           : 31st December 2017 - new year eve :)
# ----------------------------------------------

from PIL import Image
import os
import cv2
import numpy as np
import matplotlib.pyplot as plt
from scipy.stats import itemfreq
from color_recognition_api import knn_classifier as knn_classifier


def color_histogram_of_test_image(test_src_image):

    # load the image
    image = test_src_image

    chans = cv2.split(image)
    colors = ('b', 'g', 'r')
    features = []
    feature_data = ''
    counter = 0
    for (chan, color) in zip(chans, colors):
        counter = counter + 1

        hist = cv2.calcHist([chan], [0], None, [256], [0, 256])
        features.extend(hist)

        # find the peak pixel values for R, G, and B
        elem = np.argmax(hist)

        if counter == 1:
            blue = str(elem)
        elif counter == 2:
            green = str(elem)
        elif counter == 3:
            red = str(elem)
            feature_data = red + ',' + green + ',' + blue
            #print(feature_data)

    with open('/home/ubuntu/color_recognition/src/test.data', 'w') as myfile:
        myfile.write(feature_data)


def color_histogram_of_training_image(img_name):

    # detect image color by using image file name to label training data
      
    if 'beige' in img_name:
        data_source = 'beige'
    elif 'black' in img_name:
        data_source = 'black'
    elif 'brown' in img_name:
        data_source = 'brown'
    elif 'burgundy' in img_name:
        data_source = 'burgundy'
    elif 'dark_blue' in img_name:
        data_source = 'dark_blue'
    elif 'green' in img_name:
        data_source = 'green'
    elif 'grey' in img_name:
        data_source = 'grey'
    elif 'olive_green' in img_name:
        data_source = 'olive_green'
    elif 'orange' in img_name:
        data_source = 'orange'
    elif 'pink' in img_name:
        data_source = 'pink'
    elif 'purple' in img_name:
        data_source = 'purple'
    elif 'red' in img_name:
        data_source = 'red'
    elif 'turquoise' in img_name:
        data_source = 'turquoise'
    elif 'white' in img_name:
        data_source = 'white'
    elif 'yellow' in img_name:
        data_source = 'yellow'



    # load the image
    image = cv2.imread(img_name)

    chans = cv2.split(image)
    colors = ('b', 'g', 'r')
    features = []
    feature_data = ''
    counter = 0
    for (chan, color) in zip(chans, colors):
        counter = counter + 1

        hist = cv2.calcHist([chan], [0], None, [256], [0, 256])
        features.extend(hist)

        # find the peak pixel values for R, G, and B
        elem = np.argmax(hist)

        if counter == 1:
            blue = str(elem)
        elif counter == 2:
            green = str(elem)
        elif counter == 3:
            red = str(elem)
            feature_data = red + ',' + green + ',' + blue

    with open('/home/ubuntu/color_recognition/src/training.data', 'a') as myfile:
        myfile.write(feature_data + ',' + data_source + '\n')

def training():
  
    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/beige'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/beige/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/black'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/black/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/brown'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/brown/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/burgundy'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/burgundy/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/dark_blue'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/dark_blue/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/green'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/green/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/grey'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/grey/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/olive_green'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/olive_green/' + f)	

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/orange'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/orange/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/pink'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/pink/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/purple'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/purple/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/red'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/red/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/turquoise'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/turquoise/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/white'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/white/' + f)

    for f in os.listdir('/home/ubuntu/color_recognition/src/training_dataset/yellow'):
        color_histogram_of_training_image('/home/ubuntu/color_recognition/src/training_dataset/yellow/' + f)

