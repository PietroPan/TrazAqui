import Common.Const;
import MVC.Controller.*;
import MVC.Model.Data;
import MVC.Model.InterfaceData;
import MVC.View.InterfacePrinter;
import MVC.View.Printer;

import java.io.IOException;
import java.io.Serializable;

public class Main implements Serializable {
    public static void main(String[] args) {
        Const c;
        InterfacePrinter p=new Printer();
        InterfaceData model = new Data();
        try {
            c=new Const();
            c.initConsts();
            model.readFile();
        }
        catch (IOException e) {
            p.exception(e.getLocalizedMessage());
            return;
        }
        InterfaceController m = new Controller(model,p);
        m.menu();
    }
}
