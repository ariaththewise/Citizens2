package net.citizensnpcs.trait;

import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.util.Messages;
import net.citizensnpcs.util.Messaging;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Ageable;

public class Age extends Trait implements Toggleable {
    @Persist
    private int age = 0;
    private Ageable ageable;
    @Persist
    private boolean locked = true;

    public Age() {
        super("age");
    }

    public void describe(CommandSender sender) {
        Messaging.sendTr(sender, Messages.AGE_TRAIT_DESCRIPTION, npc.getName(), age, locked);
    }

    private boolean isAgeable() {
        return ageable != null;
    }

    @Override
    public void onSpawn() {
        if (npc.getBukkitEntity() instanceof Ageable) {
            Ageable entity = (Ageable) npc.getBukkitEntity();
            entity.setAge(age);
            entity.setAgeLock(locked);
            ageable = entity;
        } else
            ageable = null;
    }

    @Override
    public void run() {
        if (!locked && isAgeable())
            age = ageable.getAge();
    }

    public void setAge(int age) {
        this.age = age;
        if (isAgeable())
            ageable.setAge(age);
    }

    @Override
    public boolean toggle() {
        locked = !locked;
        if (isAgeable())
            ageable.setAgeLock(locked);
        return locked;
    }

    @Override
    public String toString() {
        return "Age{age=" + age + ",locked=" + locked + "}";
    }
}