from locust import HttpUser, task, between
import random

class MyUser(HttpUser):
    host = "http://localhost:8000"

    wait_time = between(0.5, 1.5)

    @task
    def crear_libro(self):
        payload = {
            "titulo": f"Libro {random.randint(1, 1000)}",
            "anioPublicacion": str(random.randint(2000, 2024)),
            "editorial": f"Editorial {random.randint(1, 100)}",
            "direccion": f"Direccion {random.randint(1, 500)}",
            "isbn": f"978-{random.randint(1000000000, 9999999999)}",
            "resumen": f"Resumen del libro {random.randint(1, 1000)}",
            "idioma": random.choice(["Español", "Inglés", "Francés", "Alemán"]),
            "genero": random.choice(["Ficción", "No ficción", "Ciencia", "Historia", "Biografía"]),
            "numeroPaginas": str(random.randint(50, 800)),
            "edicion": f"{random.randint(1, 10)}ª edición",
            "idAutor": random.choice([1, 2])
        }
        self.client.post("/libro", json=payload)