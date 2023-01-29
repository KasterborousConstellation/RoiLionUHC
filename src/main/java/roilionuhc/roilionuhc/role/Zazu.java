package roilionuhc.roilionuhc.role;
import fr.supercomete.head.GameUtils.Time.TimeUtility;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.head.role.Triggers.Trigger_onEpisodeTime;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import roilionuhc.roilionuhc.RLTeams;
import roilionuhc.roilionuhc.RoiLionUHC;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
public class Zazu extends Role implements Trigger_WhileAnyTime, Trigger_onEpisodeTime {
    boolean canFly=false;
    public int used_coord=0;
    int usedtime= 0;
    public Zazu(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Zazu";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.LionEarth;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
                "Vous avez la capacité de voler pendant 2min chaque épisode lorsque vous avez moins de 5♥",
                "Vous pouvez connaitre les coordonnées de n'importe quel joueur qui a moins de 8♥ avec la commande '/rlu coord' (3x/Pas de Cooldown)"
        );
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        return new ItemStack[0];
    }

    @Override
    public boolean AskIfUnique() {
        return true;
    }

    @Override
    public String AskHeadTag() {
        return null;
    }

    @Override
    public void WhileAnyTime(Player player) {
        RoiLionUHC.api.getPlayerHelper().sendActionBarMessage(player,"§7Temps restant: "+ TimeUtility.transform(120-(usedtime),"§b"));
        if(player.getHealth()<10){
            if(!canFly && usedtime<120){
                canFly=true;
                player.sendMessage("§7Vous pouvez maintenant voler.");
                player.setAllowFlight(true);
            }else{
                if(player.isFlying()){
                    usedtime++;
                }
                if(usedtime>=120){
                    canFly=false;
                    player.setAllowFlight(false);
                }
            }
        }else{
            canFly=false;
            player.setAllowFlight(false);
        }
    }

    @Override
    public void onEpisodeTime(Player player) {
        usedtime=0;
    }
}
