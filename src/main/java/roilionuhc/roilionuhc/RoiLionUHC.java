package roilionuhc.roilionuhc;
import fr.supercomete.head.GameUtils.GameMode.ModeHandler.KtbsAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import roilionuhc.roilionuhc.role.*;
public final class RoiLionUHC extends JavaPlugin {
    public static KtbsAPI api;
    public static RoiLionUHC INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE=this;
        api = Bukkit.getServicesManager().load(KtbsAPI.class);
        RLUHC uhc = new RLUHC();
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
    }

    @Override
    public void onDisable() {

    }
}