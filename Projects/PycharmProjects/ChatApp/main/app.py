# Flask imports
from flask import Flask


# initialising flask app
app = Flask(__name__)

app.static_folder='templates'