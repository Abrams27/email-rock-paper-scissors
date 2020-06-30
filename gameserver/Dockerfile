FROM python:3

ENV APP /app
RUN mkdir $APP
WORKDIR $APP

RUN wget "https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh" \
    && chmod u+x wait-for-it.sh
COPY requirements.txt .
RUN pip install -r requirements.txt

COPY *.py ./
CMD ["./wait-for-it.sh", "kafka:9092", "--", "python", "gameserver.py"]
