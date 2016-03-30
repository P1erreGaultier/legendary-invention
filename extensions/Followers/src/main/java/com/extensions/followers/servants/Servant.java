package com.extensions.followers.servants;

import com.alma.application.interfaces.monster.IMonster;

/**
 * Classe abstraite représentant un pnj quelconque
 */
public abstract class Servant {

    protected String name;
    protected String jobName;
    protected String logoFileName;
    protected int level;
    protected int hiringPrice;
    protected int uppgradePrice;

    public Servant(String name, String jobName, String logoFileName, int hiringPrice, int uppgradePrice) {
        this.name = name;
        this.jobName = jobName;
        this.logoFileName = this.getClass().getResource(logoFileName).getPath();
        this.level = 1;
        this.hiringPrice = hiringPrice;
        this.uppgradePrice = uppgradePrice;
    }

    /**
     * Méthode qui applique des effets à un monstre
     * @param monster
     */
    public abstract void affectMonster(IMonster monster);

    /**
     * Méthode qui fait améliore le servant
     */
    public abstract void upgrade();

    /**
     * Accesseur sur le nom du servant
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Accesseur sur le nom du métier du servant
     * @return
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Accesseur sur le prix d'embauche du servant
     * @return
     */
    public int getHiringPrice() {
        return hiringPrice;
    }

    /**
     * Accesseur sur le prix d'amélioration du servant
     * @return
     */
    public int getUppgradePrice() {
        return uppgradePrice;
    }

    /**
     * Accesseur sur le chemin vers l'image du servant
     * @return
     */
    public String getLogoFileName() {
        return logoFileName;
    }

    /**
     * Accesseur sur le niveau du servant
     * @return
     */
    public int getLevel() {
        return level;
    }
}
