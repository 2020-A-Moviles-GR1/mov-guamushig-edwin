/**
 * Universidad.js
 *
 * @description :: A model definition represents a database table/collection.
 * @docs        :: https://sailsjs.com/docs/concepts/models-and-orm/models
 */

module.exports = {

  attributes: {
    nombre: {
      columnName: 'nombre',
      type: 'string',
      minLength: 3,
      maxLength: 60,
      required: true,
      unique: true
    },
    fundacion: {
      columnName: 'fundacion',
      type: 'number',
      required: true,
      allowNull: false,
    },
    categoria: {
      columnName: 'categoria',
      type: 'string',
      isIn: ['A', 'B', 'C'],
      required: true,
    },
    areaConstruccion: {
      columnName: 'areaConstruccion',
      type: 'number',
      allowNull: true,
    },
    estado: {
      columnName: 'estado',
      type: 'number',
      isIn: [0, 1],
      required: false,
      defaultsTo: 1
    },
    estudiantes: {
      collection: 'estudiante',
      via: 'universidad'
    }
  },
  tableName: 'universidad',
};

