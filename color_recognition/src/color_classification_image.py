#!/usr/bin/python
# -*- coding: utf-8 -*-
# -------------------------------------------------------------------------
# --- Author         : Ahmet Ozlu
# --- Mail           : ahmetozlu93@gmail.com
# --- Date           : 8th July 2018 - before Google inside look 2018 :)
# -------------------------------------------------------------------------

import cv2
from color_recognition_api import color_histogram_feature_extraction
from color_recognition_api import knn_classifier
import os
import os.path
import sys

arg_image = int(sys.argv[1])
#print(arg_image)

# read the test image
source_image = cv2.imread('/var/www/html/ORIGIN.jpg')
source_resized = cv2.resize(source_image, (500, 500),interpolation=cv2.INTER_AREA)

if arg_image == 1:
    source_cropped = source_resized[200:300, 90:150]
else:
    source_cropped = source_resized[150:350, 150:350]

prediction = 'n.a.'

# checking whether the training data is ready
PATH = '/home/ubuntu/color_recognition/src/training.data'

if os.path.isfile(PATH) and os.access(PATH, os.R_OK):
    temp = 0
    #print ('training data is ready, classifier is loading...')
else:
    #print ('training data is being created...')
    open('/home/ubuntu/color_recognition/src/training.data', 'w')
    color_histogram_feature_extraction.training()
    #print ('training data is ready, classifier is loading...')

# get the prediction
color_histogram_feature_extraction.color_histogram_of_test_image(source_cropped)
prediction = knn_classifier.main('/home/ubuntu/color_recognition/src/training.data', '/home/ubuntu/color_recognition/src/test.data')
"""
cv2.putText(
    source_image,
    'Prediction: ' + prediction,
    (15, 45),
    cv2.FONT_HERSHEY_PLAIN,
    3,
    200,
    )
"""
# Display predicted color
print(prediction)

# Display the resulting frame
#cv2.imshow('color classifier', source_cropped)
#cv2.waitKey(1000)		
