package roilionuhc.roilionuhc.role;
import fr.supercomete.head.GameUtils.GameMode.ModeHandler.KtbsAPI;
import fr.supercomete.head.PlayerUtils.KTBSEffect;
import fr.supercomete.head.core.Main;
import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import fr.supercomete.head.role.Triggers.Trigger_WhileAnyTime;
import fr.supercomete.head.role.Triggers.Trigger_onEpisodeTime;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import roilionuhc.roilionuhc.RLTeams;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
public class Nala extends Role implements Trigger_WhileAnyTime, Trigger_onEpisodeTime {
    public boolean used = false;
    final KtbsAPI api = Bukkit.getServicesManager().load(KtbsAPI.class);
    boolean found_simba= false;

    public Nala(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return "Nala";
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return RLTeams.LionEarth;
    }

    @Override
    public List<String> askRoleInfo() {
        return Arrays.asList(
            "Quand vous croisez Simba pour la première fois vous obtenez résistance 1.",
                "Vous avez la commande '/rlu death' qui vous permet de savoir le nombre de kill d'un joueur. De plus, vous avez 1 chance sur 5 d'obtenir le pseudo de ceux qui ont été tué par ce joueur. (Utilisable 1x par épisode)",
                "Lorsque vous utilisez votre commande '/rlu death', la cible sera averti, de plus si cette personne a tué un joueur de la Terre des Lions, vous obtiendrez 15% de force pendant 1min30."
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
    int tick =0;
    @Override
    public void WhileAnyTime(Player player) {
        if(!found_simba){
            tick=(tick+1)%3;
            if(tick==0) {
                for(final Player other : Bukkit.getOnlinePlayers()) {
                    if (Main.getPlayerlist().contains(other.getUniqueId())) {
                        if (!other.getUniqueId().equals(player.getUniqueId())) {
                            if(other.getLocation().getWorld().equals(player.getWorld())){
                                if(other.getLocation().distance(player.getLocation())<5){
                                    final Role role = api.getRoleProvider().getRoleOf(other);
                                    if(role instanceof Simba){
                                        player.sendMessage("§aVous avez rencontré Simba, vous obtenez résistance 1.");
                                        found_simba=true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{
            api.getPotionEffectProvider().applyPotionEffect(player,new KTBSEffect(PotionEffectType.DAMAGE_RESISTANCE,0,20*3));
        }
    }

    @Override
    public void onEpisodeTime(Player player) {
        if(used){
            player.sendMessage("Vous pouvez de nouveau utiliser votre '/rl death'");
        }
        used=false;
    }
}