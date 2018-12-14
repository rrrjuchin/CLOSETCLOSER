import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from apyori import apriori

class Result:
    def __init__(self, base, add, support, confidence, lift):
        self.__base = base
        self.__add = add
        self.__support = support
        self.__confidence = confidence
        self.__lift = lift
    def getBase(self):
        return self.__base
    def getAdd(self):
        return self.__add
    def getSupport(self):
        return self.__support
    def getConfidence(self):
        return self.__confidence
    def getLift(self):
        return self.__lift
    def __lt__(self, other):
        return self.__lift < other.__lift


store_data = pd.read_csv('category_bottom.csv', header=None)

records = []
length = len(store_data)

for i in range(0, length):
    records.append([str(store_data.values[i,j]) for j in range(0, 2)])

association_rules = apriori(records, min_support=0.0001, min_confidence=0.0, min_lift=0, min_length=2 )
association_results = list(association_rules)

results = []
for item in association_results:
    pair = item[0]
    items = [x for x in pair]
    if len(items) == 2:
        results.append(Result(items[0], items[1], item[1], item[2][0][2], item[2][0][3]))

print (len(results))
results.sort(reverse=True)

for item in results:
    if item.getBase() == "Sweater":
        print("Rule: " + item.getBase() + " -> " + item.getAdd())
        print("Support: " + str(item.getSupport()))
        print("Confidence: " + str(item.getConfidence()))
        print("Lift: " + str(item.getLift()))
        print("=====================================")    
