# to be used in similar to app/data_warehouse/main.py when new pipeline arrives
from app.staging.ga_pipeline import GaSession
pipelines = {
    "GA_SESSION": GaSession,
}
