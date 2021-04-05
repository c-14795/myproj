from app import app

from objects import Task, User
from flask import request, render_template, redirect, url_for, flash
from flask_login import current_user, login_user, login_required, logout_user

import json

response = {'RESP': 'Success'}


@app.route("/dummy")
def dummy():
    Task(task_description=str("task_description")[:100], task_state="yet to complete").add_task()
    return "FF"


@app.route("/l")
@login_required
def homed():
    User.print_users()
    return str(Task.get_list_of_tasks())


@app.route('/login', methods=['GET', 'POST'])
def login():
    try:

        if current_user.is_authenticated:
            print "user authenticated..."

            return redirect(url_for('index'))

        if request.method == 'GET':
            return render_template('login.html')

        print request.form

        username = request.form['username']

        password = request.form['password']

        registered_user = User.query.filter_by(user_id=username).first()
        print registered_user
        if registered_user is None:
            flash('Username is invalid', 'error')
            print "fail"
            return redirect(url_for('login'))

        if not registered_user.check_password(password):
            flash('Password is invalid', 'Error')
            return redirect(url_for('login'))

        login_user(registered_user)

        flash('Logged in successfully')

        return redirect(url_for('index'))

    except Exception as e:
        print  e.message
        return json.dumps({'response': 'Fail'})

@app.route("/update_task", methods=["POST"])
@login_required
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



@app.route('/logout')
@login_required
def logout():
    try:
        logout_user()

        return render_template('login.html')

    except Exception as e:

        print (e)
        return json.dumps({'response': 'Fail'})


@app.route("/", methods=['get', 'post'])
@login_required
def index():
    print "f"
    if current_user.role == 'admin':
        return render_template('view.html', tasks=Task.get_list_of_tasks())
    user_tasks = Task.get_tasks_by_user_id(current_user.user_id)

    return render_template('view.html', tasks=user_tasks)


@app.route("/task", methods=['get', 'post'])
@login_required
def task():
    if request.method == 'GET':
        return render_template('Task.html')
    Task(task_description=request.form['desc'],task_completion_time=request.form['ETA'],sch_start_time=request.form['STT'],user_id=current_user.user_id).add_task()
    return redirect(url_for('index'))

@app.after_request
def after_request(resp):
    resp.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
    return resp