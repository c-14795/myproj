from app import app

try:
    from app import create_db
except:
    pass
app.run('0.0.0.0', 8977, debug=True, threaded=True)
