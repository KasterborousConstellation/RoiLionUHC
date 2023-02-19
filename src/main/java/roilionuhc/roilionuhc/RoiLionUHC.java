package roilionuhc.roilionuhc;
import fr.supercomete.head.GameUtils.GameMode.ModeHandler.KtbsAPI;
import fr.supercomete.head.GameUtils.GameMode.ModeModifier.Groupable;
import fr.supercomete.head.core.Main;
import fr.supercomete.head.structure.Structure;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import roilionuhc.roilionuhc.role.*;
import java.util.ArrayList;
public final class RoiLionUHC extends JavaPlugin implements Groupable {
    public static KtbsAPI api;
    public static RoiLionUHC INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE=this;
        api = Bukkit.getServicesManager().load(KtbsAPI.class);
        RLUHC uhc = new RLUHC();
        Bukkit.getServer().getPluginManager().registerEvents(new BlockPlaceAndDestroyEvent(),this);
        Bukkit.getServer().getPluginManager().registerEvents(new CocoGUI(),this);
        api.getModeProvider().registerMode(uhc);
        api.getRoleProvider().registerRole(uhc, Simba.class);
        api.getRoleProvider().registerRole(uhc, Shenzi.class);
        api.getRoleProvider().registerRole(uhc, Banzai.class);
        api.getRoleProvider().registerRole(uhc, Ed.class);
        api.getRoleProvider().registerRole(uhc, Hyenes.class);
        uhc.RegisterRole(Nala.class);
        uhc.RegisterRole(Timon.class);
        uhc.RegisterRole(Pumba.class);
        uhc.RegisterRole(Lionnes.class);
        uhc.RegisterRole(Zazu.class);
        uhc.RegisterRole(Kiara.class);
        uhc.RegisterRole(Mufasa.class);
        uhc.RegisterRole(Sarabi.class);
        uhc.RegisterRole(Kovu.class);
        uhc.RegisterRole(Scar.class);
        uhc.RegisterRole(Kion.class);
        uhc.RegisterRole(Rani.class);
        uhc.RegisterRole(Fauve.class);
        uhc.RegisterRole(Vitani.class);
        uhc.RegisterRole(Nuka.class);
        uhc.RegisterRole(Zira.class);
        uhc.RegisterRole(Rafiki.class);
        ArrayList<Structure> structures = new ArrayList<>();
        structures.add(Main.structurehandler.extractStructure(this.getClass(),"banzai"));
        uhc.setStructure(structures);
    }

    @Override
    public void onDisable() {

    }
}