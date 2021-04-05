from app import db,app
from main.objects import Tasks, User

from flask import request, render_template, redirect, url_for
import json


@app.route("/")
def home():
    u=User(username='a',email='b')
    db.session.add(u)
    db.session.commit()
    users= User.query.all()
    return [user.username for user in users]