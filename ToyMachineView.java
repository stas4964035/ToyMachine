import java.io.*;
import java.util.Scanner;

public class ToyMachineView {
    Scanner scanner = new Scanner(System.in);
    Machine machine;
    public ToyMachineView(Machine m) throws IOException, ClassNotFoundException {
        machine = m;
        System.out.println("Аппарат игрушек запущен.");
        System.out.println("Игрушек в списке: " + machine.toyList.size());
        System.out.println("Слотов в аппарате: " + machine.slots.size());
        System.out.println("Игрушек в слотах всего: " + machine.toyCount);
        System.out.print("Введите команду(help - список команд): ");
        this.commander(scanner.nextLine());

    }
    void helpMenu() throws IOException, ClassNotFoundException {
        System.out.println("toylist - список игрушек");
        System.out.println("toyadd - добавление игрушки");
        System.out.println("toyedit - редактирование игрушки");
        System.out.println("toydel - удаление игрушки из списка");
        System.out.println("slotlist - список слотов в аппарате");
        System.out.println("slotadd - добавление слота в аппарат");
        System.out.println("slotedit - редактирование слотов в аппарате");
        System.out.println("slotdel - удаление слотов в аппарате");
        System.out.println("start - начать розыгрыш");
        System.out.println("save - сохранить состояние аппарата в файл");
        System.out.println("load - загрузить состояние аппарата из файла");
        this.commander(scanner.nextLine());
    }

    
    void commander(String command) throws IOException, ClassNotFoundException {
        switch (command){
            case("help"):
                this.helpMenu();
                break;
            case("toylist"):
                this.toyListMenu();
                break;
            case("toyadd"):
                this.toyAddMenu();
                break;
            case("toyedit"):
                this.toyEditMenu();
                break;
            case("toydel"):
                this.toyDelMenu();
                break;
            case("slotlist"):
                this.slotListMenu();
                break;
            case("slotadd"):
                this.slotAddMenu();
                break;
            case("slotedit"):
                this.slotEditMenu();
                break;
            case("slotdel"):
                this.slotDelMenu();
                break;
            case("start"):
                this.startMenu();
                break;
            case("save"):
                this.saveMenu();
                break;
            case("load"):
                this.loadMenu();
                break;            
            default:
                System.out.println("Комманда не найдена, попробуйте еще раз(help - список всех команд)");
                this.commander(scanner.nextLine());
                break;

        }
    }

    private void loadMenu() throws IOException, ClassNotFoundException {

        FileInputStream is = new FileInputStream("save.d2");
        ObjectInputStream ois = new ObjectInputStream(is);
        ToyMachineSaver loader = (ToyMachineSaver) ois.readObject();
        this.machine.toyList = loader.toyList;
        this.machine.slots = loader.slots;
        System.out.println("Если ошибок нет, то похоже текущее старое аппарата загружено.");
        this.commander(scanner.nextLine());
    }

    private void saveMenu() throws IOException, ClassNotFoundException {
        ToyMachineSaver saver = new ToyMachineSaver(this.machine.toyList, this.machine.slots);
        FileOutputStream os = new FileOutputStream("save.d2");
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(saver);
        oos.close();
        System.out.println("Если ошибок нет, то похоже текущее состояние аппарата сохранено.");
        this.commander(scanner.nextLine());
    }

    private void startMenu() throws IOException, ClassNotFoundException {
        if(this.machine.toyCount > 0){
            System.out.println("Игрушек в списке: " + machine.toyList.size());
            System.out.println("Слотов в аппарате: " + machine.slots.size());
            System.out.println("Игрушек в слотах всего: " + machine.toyCount);
            System.out.println("Запускаем розыгрыш!");
            System.out.println("Ваше результат: " + this.machine.start());
        }else {
            System.out.println("Аппарат пуст!");
        }
        this.commander(scanner.nextLine());
    }

    private void slotDelMenu() throws IOException, ClassNotFoundException {
        System.out.println(this.machine.slotList());
        System.out.println("Удаление слота не удалит игрушку из общего списка, но игрушка в розыгрыше участвовать не будет.");
        System.out.print("Введите ID слота для удаления: ");
        int slotID = Integer.parseInt(scanner.nextLine());
        this.machine.deleteSlotByID(slotID);
        this.slotListMenu();
    }

    private void slotEditMenu() throws IOException, ClassNotFoundException {
        System.out.println(this.machine.slotList());
        System.out.print("Введите ID слота для редактирования: ");
        int slotID = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите новое количество игрушек в слоте: ");
        int toyCount = Integer.parseInt(scanner.nextLine());
        this.machine.addSlot(slotID, toyCount);
        this.slotListMenu();
    }

    private void slotAddMenu() throws IOException, ClassNotFoundException {
        System.out.println(this.machine.toyList());
        System.out.println("Добавление слота с уже существующей игрушкой удалит старый слот.");
        System.out.print("Введите ID игрушки для добавления в слот: ");
        int toyID = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите количество игрушек в слоте: ");
        int toyCount = Integer.parseInt(scanner.nextLine());
        this.machine.addSlot(toyID, toyCount);
        this.slotListMenu();
    }

    private void slotListMenu() throws IOException, ClassNotFoundException {
        System.out.println(this.machine.slotList());
        this.commander(scanner.nextLine());
    }

    private void toyDelMenu() throws IOException, ClassNotFoundException {
        System.out.println(this.machine.toyList());
        System.out.println("Все слоты с удаляемой игрушкой будут так же удалены.");
        System.out.print("Ввежите ID игрушки для удаления: ");
        int id = Integer.parseInt(scanner.nextLine());
        this.machine.deleteToyByID(id);
        this.toyListMenu();
    }

    private void toyEditMenu() throws IOException, ClassNotFoundException {
        System.out.println(this.machine.toyList());
        System.out.print("Ввежите ID игрушки для редактирования: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите новое имя: ");
        String newTitle = scanner.nextLine();
        System.out.print("Введите новый вес(шанс выпадения): ");
        double newWeight = Double.parseDouble(scanner.nextLine());
        this.machine.editToy(id, newTitle, newWeight);
        this.toyListMenu();
    }

    private void toyAddMenu() throws IOException, ClassNotFoundException {
        System.out.print("Название новой игрушки: ");
        String newTitle = scanner.nextLine();
        System.out.print("Вес(шанс выпадения) новой игрушки: ");
        double newWeight = Double.parseDouble(scanner.nextLine());
        this.machine.addToy(newTitle, newWeight);
        this.toyListMenu();
    }
    void toyListMenu() throws IOException, ClassNotFoundException {
        System.out.println(this.machine.toyList());
        this.commander(scanner.nextLine());
    }
}
