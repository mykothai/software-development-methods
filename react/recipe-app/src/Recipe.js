import React from 'react';


const Recipe = ({title, calories, image, dietLabel, healthLabel, ingredients, time, servings, url}) => {
    return(
        <div>
            <h1>{title}</h1>
            <img src={image} alt=""></img>
            <p>
                Calories: {calories.toFixed(2)} <br/>
                Tags: 
                <ul style={{ listStyleType: "none" }}>
                    {dietLabel.map((dietLabel, index) => (
                        <li key={index}>{dietLabel} </li>
                    ))}
                    {healthLabel.map((healthLabel, index) => (
                        <li key={index}>{healthLabel} </li>
                    ))}
                </ul>
                Time: {time} minutes <br/>
                Serves: {servings} <br/>
            </p>
            <p>Ingredients:
                <ul style={{ listStyleType: "none" }}>
                    {ingredients.map(ingredients =>(
                        <li>{ingredients.text}</li>
                    ))}
                </ul>
            </p>
            <p>Link: 
                <a href={url}> {url}</a>
            </p>
        </div>
    );
};

export default Recipe;