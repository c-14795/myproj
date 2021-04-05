# Flask imports
from flask import Flask
from flask_login import LoginManager
from flask_sqlalchemy import SQLAlchemy

# System imports
import os

#internal imports



# initialising flask main
app = Flask(__name__)
app.static_folder = 'templates'
path = os.path.join(os.getcwd(), 'app.db')
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = 'false'
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///' + path

db = SQLAlchemy()
login_manager = LoginManager()
db.init_app(app)
login_manager.init_app(app)

if not os.path.exists(path):
    print("cratin")
    with app.app_context():
        db.create_all()
        db.session.commit()
