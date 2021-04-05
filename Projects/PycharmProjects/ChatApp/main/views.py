# Flask imports
from flask import request, render_template, redirect, url_for

# internal imports
from main import app
from readFile import data as d

# System or lib imports
import json


@app.route("/getAnswer", methods=["POST"])
def get_answer():
    resp = {}
    try:
        data = request.get_json()
        print d[data['query']]
        resp['answer'] = d[data['query']]
    except Exception as e:
        print e.message
        resp['answer'] = e.message
    return json.dumps(resp)


@app.route("/")
def home():
    print "JJ"
    return render_template("collapsible_chat_widget.html")
