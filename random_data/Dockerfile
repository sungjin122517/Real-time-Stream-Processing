# Use a base Python image
FROM python:3.9-slim

# Set the working directory in the container
WORKDIR /app

# Copy the requirements file into the container at /app
COPY requirements.txt /app

# Install any dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the Python scripts into the container at /app
COPY python_producer.py /app
COPY generate_news.py /app

# Set the entry point to run the command
CMD ["python", "python_producer.py"]