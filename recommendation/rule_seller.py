import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
from apyori import apriori
import firebase_admin
import sys
from firebase_admin import credentials
from firebase_admin import firestore



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
    def getLift(self):
        return self.__lift
    def __lt__(self, other):
        return self.__lift < other.__lift


def Association(sex, clothe_type, season, top_uid1, top_uid2, top_uid3):

    db = firestore.client()

    doc_ref = db.collection(u'Transaction_Seller').document(sex).collection(clothe_type).get()
    records = []

    for i in doc_ref:
        n = int(i.get("num"))
        for k in range(n):
            records.append([i.get("top"), i.get("clothes")])

    association_rules = apriori(records, min_support=0.00001, min_confidence=0, min_lift=0, min_length=2 )
    association_results = list(association_rules)


    results = []
    for item in association_results:
        pair = item[0]
        items = [x for x in pair]
        if len(items) == 2:
            results.append(Result(items[0], items[1], item[1], item[2][0][2], item[2][0][3]))

    results.sort(reverse=True)
    
    match = []

    for i in results:
        if i.getBase() == top_uid1:
            match.append(i.getAdd())
            break
        elif i.getAdd() == top_uid1:
            match.append(i.getBase())
            break

    for i in results:
        if i.getBase() == top_uid2:
            match.append(i.getAdd())
            break
        elif i.getAdd() == top_uid2:
            match.append(i.getBase())
            break

    for i in results:
        if i.getBase() == top_uid3:
            match.append(i.getAdd())
            break
        elif i.getAdd() == top_uid3:
            match.append(i.getBase())
            break

    
    return match



if __name__ == "__main__":

    sex = sys.argv[1]
    season = sys.argv[2]
    top_uid1 = sys.argv[3]
    top_uid2 = sys.argv[4]
    top_uid3 = sys.argv[5]

    if (not len(firebase_admin._apps)):
        cred = credentials.Certificate('/home/ubuntu/recommendation/closet-closer-firebase.json')
        firebase_admin.initialize_app(cred, {
        'projectId': 'closet-closer',
        })


    bottom_match = []
    outer_match = []
    
    
    bottom_match = Association(sex, "bottom", season, top_uid1, top_uid2, top_uid3)
    outer_match = Association(sex, "outer", season, top_uid1, top_uid2, top_uid3)

    print( bottom_match[0]+"\n"+bottom_match[1]+"\n"+ bottom_match[2]+"\n"+outer_match[0]+"\n"+outer_match[1]+"\n"+outer_match[2] )


