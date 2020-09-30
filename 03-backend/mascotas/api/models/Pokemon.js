/**
 * Pokemon.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {
  attributes: {
    nombre: {
      type: 'string'
    },
    usuario: {
      model: 'usuario'
    },
    batalla: {
      model: 'batalla'
    }
  },
  tableName: 'pokemon',
};

