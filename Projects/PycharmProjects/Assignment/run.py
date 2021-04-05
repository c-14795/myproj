from app import app

try:
    from app import create_db
except:
    pass
app.run('0.0.0.0', 8976, debug=True, threaded=True)
