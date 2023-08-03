import java.util.*;

public class Machine {
    HashMap<Integer, Toy> toyList = new HashMap<>();
    LinkedHashMap<Integer, Integer> slots = new LinkedHashMap<>();
    double totalWeight = 0;
    int toyCount = 0;

    public Machine(boolean testData) {
        if (testData) {
            this.addToy("Кукла Маша", 30);
            this.addToy("Кукла Даша", 30);
            this.addToy("Плюшевый мишка", 45);
            this.addToy("Шерстяной жираф", 20);
            this.addToy("Покемон Замай", 5);
            this.addToy("Покемон КПСС", 5);

            this.addSlot(5, 2);
            this.addSlot(4, 1);
            this.addSlot(3, 1);
            this.addSlot(2, 1);
            this.addSlot(1, 3);
        }
    }


    void addToy(String title, double weight) {
        this.toyList.put(this.toyList.size() + 1, new Toy(this.toyList.size() + 1, title, weight));
    }

    void addSlot(int toyID, int toyCount) {
        this.slots.put(toyID, toyCount);
        this.calcWeight();
    }

    Toy getToyByID(Integer id) {
        return this.toyList.get(id);
    }

    void calcWeight() {
        double res = 0;
        int toyCount = 0;
        for (Map.Entry<Integer, Integer> slot : this.slots.entrySet()) {
            if (slot.getValue() > 0) {
                res += slot.getValue() * this.getToyByID(slot.getKey()).getWeight();
                toyCount += slot.getValue();
            }
        }
        this.setTotalWeight(res);
        this.setToyCount(toyCount);

    }


    void decrementCountInSlot(Integer id) {
        if (this.slots.get(id) > 0) {
            this.slots.put(id, this.slots.get(id) - 1);
            this.calcWeight();
        }
    }

    String start() {
        /*
         * Смешиваем слоты.
         * Логика выбора игрушки: получаем случайное число от 0 до общего веса игрушек в слотах, учитывая количество в слоте,
         * далее проходимся по списку игрушек и добавляем во временную перемнную сумму веса ишрушек слота.
         * Условие выпадение игрушки это то, что временная переменная(сумарный вес игрушек в цикле) становится меньше слчайного числа.
         * Уменьшаем количество игрушек в слоте и пересчет общего веса в слотах.
         * Игнорирование пустых слотов.
         * */
        if(this.getToyCount() > 0){
            this.shuffleSlots();
            double rand = new Random().nextDouble(this.getTotalWeight());
            double res = 0;
            Toy resToy = null;

            for (Map.Entry<Integer, Integer> slot : this.slots.entrySet()) {
                if (slot.getValue() > 0) {
                    res += slot.getValue() * this.getToyByID(slot.getKey()).getWeight();
                }
                if (res >= rand) {
                    resToy = this.getToyByID(slot.getKey());
                    this.decrementCountInSlot(slot.getKey());
                    break;
                }
            }
            return resToy.toString();
        }else{
            return "Аппарат пуст! Наполните его игрушками!";
        }
    }

    void shuffleSlots() {
        List<Integer> keys = new ArrayList<>(this.slots.keySet());
        LinkedHashMap<Integer, Integer> shuffled = new LinkedHashMap<>();
        Collections.shuffle(keys);
        for (Integer key : keys) {
            shuffled.put(key, this.slots.get(key));
        }
        this.slots = shuffled;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public int getToyCount() {
        return toyCount;
    }

    public void setToyCount(int toyCount) {
        this.toyCount = toyCount;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "toyList=" + toyList +
                '}';
    }
}
