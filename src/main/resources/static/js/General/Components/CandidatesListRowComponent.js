var CandidatesListRowComponent = React.createClass({

   render: function() {

     return (
        <tr key={index} className='small'>
           <td>{this.props.name}</td>
           <td>{this.props.surname}</td>
           <td>{this.props.birthDate}</td>
           <td>{this.props.partijosPavadinimas}</td>
           <td>{this.props.numberInParty}</td>
           <td>{this.props.countyName}</td>
        </tr>
     );
   }
});
CandidatesListRowComponent.contextTypes = {
      router: React.PropTypes.object.isRequired,
    };
   window.CandidatesListRowComponent = CandidatesListRowComponent;
