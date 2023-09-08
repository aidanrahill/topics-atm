import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ATM {

    HashMap<String, Double> vault;

    public static void main(String[] args) {
        ATM atm = new ATM();
        try {
            atm.openAccount("bob", 500);
            atm.openAccount("stuart", 100);
            atm.withdrawMoney("bob", 200);
            atm.depositMoney("stuart", 50);
            atm.transferMoney("bob", "stuart", 200);
            atm.audit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ATM() {
        vault = new HashMap<String, Double>();
    }

    public void openAccount(String id, double dough) throws Exception {
        if (vault.get(id) != null) {
            throw new Exception("ID is already in system.");
        }
        vault.put(id, dough);
    }

    public void closeAccount(String id) throws Exception {
        if (vault.get(id) == null) {
            throw new Exception("Account does not exist.");
        }
        if (vault.get(id) > 0.0) {
            throw new Exception("Money still in account; Withdraw first.");
        }
        vault.remove(id);
    }

    public double checkBalance(String id) throws Exception {
        if (vault.get(id) == null) {
            throw new Exception("Account does not exist.");
        }
        return vault.get(id);
    }

    public double depositMoney(String id, double racks) throws Exception {
        if (vault.get(id) == null) {
            throw new Exception("Account does not exist.");
        }
        double money = vault.get(id);
        vault.remove(id);
        openAccount(id, money + racks);
        return racks;
    }

    public double withdrawMoney(String id, double moolah) throws Exception {
        if (vault.get(id) == null) {
            throw new Exception("Account does not exist.");
        }
        if (vault.get(id) < moolah) {
            throw new Exception("are you in college?");
        }
        double money = vault.get(id);
        vault.remove(id);
        openAccount(id, money - moolah);
        return moolah;
    }

    public boolean transferMoney(String sendID, String recieveID, double bands) throws Exception {
        if (vault.get(sendID) == null || vault.get(recieveID) == null) {
            throw new Exception("Account does not exist.");
        }
        if (vault.get(sendID) < bands) {
            throw new Exception("are you in college?");
        }
        withdrawMoney(sendID, bands);
        depositMoney(recieveID, bands);
        return true;
    }

    public void audit() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("AccountAudit.txt"))) {
            for (int i = 0; i < vault.size(); i++) {
                writer.write(vault.keySet().toArray()[i] + ": " + vault.values().toArray()[i] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}