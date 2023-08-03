public class ToyMachinePresenter {
    public static void main(String[] args) {
        Machine m = new Machine(true);
        while (m.getToyCount() > 0) {
            System.out.println(m.start());
        }
        System.out.println(m.start());
    }


}
