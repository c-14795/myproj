# System imports
import os

# Flask imports
from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_login import LoginManager
from werkzeug.security import generate_password_hash

# initialising flask app and creating  sqllite db
app = Flask(__name__)
path = os.path.join(os.getcwd(), 'app.db')
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + path
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = 'false'
app.secret_key = os.urandom(128)
db = SQLAlchemy(app)
login = LoginManager(app)
login.login_view = 'login'

from objects import User,Task

if not os.path.exists(path):
    print "hi"
    db.create_all()
    db.session.add(
        User(user_id='c14795', email='c14795@domain.com', password_hash=generate_password_hash('secret'), role='admin'))
    db.session.add(
        User(user_id='Muz14', email='Muz14@domain.com', password_hash=generate_password_hash('secret'), role='user'))
    db.session.add(Task(task_description="Python package needs to be installed",user_id="Muz14"))
    db.session.commit()
