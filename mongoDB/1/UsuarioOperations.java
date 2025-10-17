
public class UsuarioOperations {
    public static void main(String[] args) {

        // Conexão com o MongoDB (local)
        MongoDBConnection connection = new MongoDBConnection();
        MongoDatabase database = connection.getDatabase();
        MongoCollection<Usuario> collection = database.getCollection("usuarios");

        System.out.println("=== Inserindo documentos ===");
        Usuario u1 = new Usuario("Alice", 25);
        Usuario u2 = new Usuario("Bob", 30);
        Usuario u3 = new Usuario("Charlie", 35);

        // Insere os registros
        collection.insertMany(Arrays.asList(u1, u2, u3));
        listar(collection);

        System.out.println("\n=== Atualizando idade de Bob para 32 ===");
        collection.updateOne(Filters.eq("nome", "Bob"), Updates.set("idade", 32));
        listar(collection);

        System.out.println("\n=== Removendo usuário Charlie ===");
        collection.deleteOne(Filters.eq("nome", "Charlie"));
        listar(collection);

        mongoClient.close();
    }

    // Método auxiliar para listar documentos da coleção
    private static void listar(MongoCollection<Document> collection) {
        System.out.println("Registros atuais na coleção:");
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }
    }
}