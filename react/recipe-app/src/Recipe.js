import React from 'react';
import style from './recipe.module.css';

const Recipe = ({title, calories, image, dietLabel, healthLabel, ingredients, time, servings, url}) => {
    return(
        <div className={style.recipe}>
            <h1 style={{color: 'black', paddingTop: 0}}>{title}</h1>
            <p className={style.info}>
                <img className={style.image} src={image} alt=""></img>
                <b>Calories</b>: {calories.toFixed(2).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')} <br/>
                <b>Labels</b>: 
                <ul className={style.ul}>
                    {dietLabel.map((dietLabel, index) => (
                        <li key={index}>{dietLabel} </li>
                        ))}
                    {healthLabel.map((healthLabel, index) => (
                        <li key={index}>{healthLabel} </li>
                        ))}
                </ul>
                <b>Time</b>: {time} minutes <br/>
                <b>Serves</b>: {servings} <br/>
            </p>
            <p className={style.info}>
                <b>Ingredients</b>:
                <ul className={style.ul}>
                    {ingredients.map(ingredients =>(
                        <li>{ingredients.text}</li>
                        ))}
                </ul>
            </p>
            <p><b>Link</b>:&nbsp;
                <a href={url}>{url}</a>
            </p>
        </div>
    );
};

export default Recipe;