package roilionuhc.roilionuhc.role;

import fr.supercomete.head.role.KasterBorousCamp;
import fr.supercomete.head.role.Role;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class Vitani extends Role {
    public Vitani(UUID owner) {
        super(owner);
    }

    @Override
    public String[] AskMoreInfo() {
        return new String[0];
    }

    @Override
    public String askName() {
        return null;
    }

    @Override
    public KasterBorousCamp getDefaultCamp() {
        return null;
    }

    @Override
    public List<String> askRoleInfo() {
        return null;
    }

    @Override
    public ItemStack[] askItemStackgiven() {
        return new ItemStack[0];
    }

    @Override
    public boolean AskIfUnique() {
        return false;
    }

    @Override
    public String AskHeadTag() {
        return null;
    }
}
