from create_db import db


class Task(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    task_description = db.Column(db.String(100))
    task_state = db.Column(db.String(15), default="yet to complete")

    def del_task(self):
        db.session.delete(self)
        db.session.commit()

    def __repr__(self):
        return 'id:{self.id}\ntask_description:{self.task_description}\ntask_state:{self.task_state}'.format(self=self)

    def add_task(self):
        db.session.add(self)
        db.session.commit()

    def update_task(self):
        self.task_state = "Completed"
        db.session.commit()

    @staticmethod
    def get_list_of_tasks():
        tasks = db.session.query(Task)
        rv = []
        for i in tasks:
            print i
            rv.append({'id': i.id, 'desc': i.task_description, 'status': i.task_state})
        db.session.commit()
        return rv
