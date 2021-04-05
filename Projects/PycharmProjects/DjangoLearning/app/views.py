from app import app

from objects import Task
from flask import request, render_template, redirect, url_for
import json

response = {'RESP': 'Success'}


@app.route("/")
def home():
    return redirect(url_for("list_tasks"))

@app.route("/hello")
def hello():
    return "Hello"

@app.route("/send_details", methods=["POST"])
def test():
    return json.dumps({'Country': 'India'})


@app.route("/create_task", methods=["POST"])
def create_task():
    try:
        data = request.get_json()
        Task(task_description=str(data["task_description"])[:100], task_state="yet to complete").add_task()
        response['RESP'] = 'Success'
    except Exception as e:
        print e.message
        response['RESP'] = 'Fail'
    return json.dumps(response)


@app.route("/delete_task", methods=["POST"])
def delete_task():
    try:
        task = Task.query.get(request.get_json().get("id"))
        task.del_task()
        response['RESP'] = 'Success'
    except Exception as e:
        print e.message
        response['RESP'] = 'Fail'
    return json.dumps(response)


@app.route("/update_task", methods=["POST"])
def update_task():
    try:
        data = request.get_json()
        task = Task.query.get(data['id'])
        task.update_task()
        response['RESP'] = 'Success'
    except Exception as e:
        print e.message
        response['RESP'] = 'Fail'
    return json.dumps(response)


@app.route("/list_tasks", methods=["GET"])
def list_tasks():
    tasks = Task.get_list_of_tasks()
    return render_template('tasks.html', tasks=tasks)
