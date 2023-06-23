    package model;

    public class Paciente {
        private String nombre;
        private long id; // assuming there is an 'id' field
        private String obraSocial;
        // other fields...

        public Paciente(String nombre) {
            this.nombre = nombre;
        }

        public Paciente() {

        }

        // getters and setters...

        public String getNombre() {
            return this.nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public long getId() {
            return this.id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getObraSocial() {
            return obraSocial;
        }

        public void setObraSocial(String obraSocial) {
            this.obraSocial = obraSocial;
        }

        // other methods...


        @Override
        public String toString() {
            return "Paciente{" +
                    "id=" + id +
                    ", nombre='" + nombre + '\'' +
                    ", obra social='" + obraSocial +
                    '}';
        }
    }
