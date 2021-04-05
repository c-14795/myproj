from create_db import db
from flask_login import UserMixin
from create_db import login
from werkzeug.security import generate_password_hash, check_password_hash
from datetime import datetime
from Utils import status_1, status_2, status_3


class User(UserMixin, db.Model):
    id = db.Column(db.Integer, primary_key=True)
    user_id = db.Column(db.String(100), unique=True)
    email = db.Column(db.String(120), index=True, unique=True)
    password_hash = db.Column(db.String(128))
    role = db.Column(db.String(30))
    tasks = db.relationship('Task', backref='owner', lazy='dynamic')

    def set_password(self, password):
        return generate_password_hash(password)

    def check_password(self, password):
        return check_password_hash(self.password_hash, password)

    def add_user(self):
        db.session.add(self)
        db.session.commit()

    @staticmethod
    def print_users():
        users = User.query.all()
        for i in users:
            print i

    def __repr__(self):
        return '<User {}>'.format(self.user_id)


class Task(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    task_description = db.Column(db.String(1000))
    task_state = db.Column(db.String(15), default=status_1)
    task_completion_time = db.Column(db.String(19), default="3500-12-12 00:00:00")
    sch_start_time = db.Column(db.String(19), default="3500-12-12 00:00:00")
    actual_end_time = db.Column(db.String(19), default="3500-12-12 00:00:00")
    actual_start_time = db.Column(db.String(19), default="3500-12-12 00:00:00")
    dependent_tasks_id = db.Column(db.String(128), default='-1')
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))

    def del_task(self):
        db.session.delete(self)
        db.session.commit()

    def __repr__(self):
        return 'id:{self.id}\ntask_description:{self.task_description}\ntask_state:{self.task_state}'.format(self=self)

    def add_task(self):
        db.session.add(self)
        db.session.commit()

    def update_task(self):
        if self.task_state == status_1:
            self.task_state = status_2
            self.actual_start_time = datetime.strftime(datetime.now(), "%Y-%m-%d %H:%M:%S")
        elif self.task_state == status_2:
            self.task_state = status_3
            self.actual_end_time = datetime.strftime(datetime.now(), "%Y-%m-%d %H:%M:%S")
        else:
            self.task_state = status_3
        db.session.commit()

    @staticmethod
    def convert_tasks(tasks):
        rv = []
        for i in tasks:
            print i
            rv.append({'id': i.id, 'desc': i.task_description, 'status': i.task_state,
                       'task_completion_time': getFormattedDate(i.task_completion_time),
                       'btn': setButtonName(i.task_state),
                       'btn_cls': setBtnType(i.task_state, getDateObj(i.task_completion_time),getDateObj(i.sch_start_time)),
                       'user_id': i.user_id, 'sch_strt_tm': getFormattedDate(i.sch_start_time),
                       'act_st_tm': getFormattedDate(i.actual_start_time),
                       'act_end_tm': getFormattedDate(i.actual_end_time),
                       'minutes_elapsed': noOfMinutesElapsed(i.task_state, getDateObj(i.actual_start_time),
                                                             getDateObj(i.actual_end_time))})
        return rv

    @staticmethod
    def get_list_of_tasks():
        tasks = Task.convert_tasks(db.session.query(Task).order_by(Task.id))
        db.session.commit()
        return tasks

    @login.user_loader
    def load_user(id):
        return User.query.get(int(id))

    @staticmethod
    def get_tasks_by_user_id(id):
        return Task.convert_tasks(Task.query.filter_by(user_id=id))


def getFormattedDate(date):
    return datetime.strftime(datetime.strptime(date, "%Y-%m-%d %H:%M:%S"), "%A, %d. %B %Y %I:%M:%S%p")


def setButtonName(status):
    if status == status_1:
        return 'Start Progress'
    elif status == status_2:
        return 'Mark as done'
    return 'Done'


def setBtnType(status, sch_end_time,sch_strt_time):
    if status == status_1:
        return 'btn btn-default'
    elif status == status_2:
        if (sch_end_time - datetime.now()).seconds < 15 * 60:
            return "btn btn-warning"
        elif (sch_end_time - datetime.now()).seconds < 30 * 60:
            return "btn btn-primary"
        elif datetime.now() > sch_end_time:
            return "btn btn-danger"
        return 'btn btn-info'
    return 'btn btn-success'


def noOfMinutesElapsed(status, start_time, end_time):
    if status == status_1:
        return 'NA'
    elif status == status_3:
        return str((end_time - start_time).seconds // 60) + " min"
    else:
        return str((datetime.now()-start_time).seconds // 60) + " min"


def getDateObj(timeInStr):
    return datetime.strptime(timeInStr, "%Y-%m-%d %H:%M:%S")
