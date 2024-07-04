package desafio.back_end.demo.autorization;

public record Authorization(
        String message
) {
    public boolean isAthorized(){
        return message.equals("autorizado");
    }

    public static class UnauthrizedTransactionException extends  RuntimeException{
        public UnauthrizedTransactionException(String message){
            super(message);
        }
    }
}
