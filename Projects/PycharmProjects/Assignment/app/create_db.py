# System imports
import os

# Flask imports
from flask import Flask
from flask_sqlalchemy import SQLAlchemy

# initialising flask app and creating  sqllite db
app = Flask(__name__)
path =  os.path.join(os.getcwd(), 'app.db')
app.config['SQLALCHEMY_DATABASE_URI'] ='sqlite:///' + path
db = SQLAlchemy(app)
from objects import Task

if not os.path.exists(path):
    db.create_all()
db.session.commit()
