package KexTools.module;

import java.util.ArrayList; 

import KexTools.module.player.*;
import KexTools.module.render.*;
import KexTools.utils.Manager;
import KexTools.module.combat.*;
import KexTools.module.exploit.*;
import KexTools.module.movement.*;

public class ModuleManager {
    private ArrayList<Module> modules = new ArrayList<Module>();
    
    public Manager<Module> module;
    
    //Powered by: Nagy Mátyás és Cl3sTerMáté

    public ModuleManager() {
        modules.add(new Sprint());
        modules.add(new Fly());
        modules.add(new NoFall());
        modules.add(new Bhop());
        modules.add(new KillAura());
        modules.add(new MotionGraph());
        modules.add(new Fullbright());
        modules.add(new ChestStealer());
        modules.add(new Strafe());
        modules.add(new TargetStrafe());
        modules.add(new ClickGUI());
        modules.add(new AntiVoid());
        modules.add(new Criticals());
        modules.add(new AutoArmor());
        modules.add(new AntiBot());
        modules.add(new LongJump());
        modules.add(new Phase());
        modules.add(new Step());
        modules.add(new Animations());
        modules.add(new ItemPhysics());
        modules.add(new Velocity());
        modules.add(new NoSlow());
        modules.add(new FastEat());
        modules.add(new Tracers());
        modules.add(new Disabler());
        modules.add(new ESP());
        modules.add(new Nametags());
        modules.add(new AutoSoup());
        modules.add(new Spider());
        modules.add(new Scaffold());
    }

    public ArrayList<Module> getModules() {
        return modules;
    }
    public Module getModuleByName(String name) {
        return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
